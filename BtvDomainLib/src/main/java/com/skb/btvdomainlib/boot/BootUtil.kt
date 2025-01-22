package com.skb.mytvlibrary.utils

import android.content.Context
import com.skb.mytvlibrary.server.service.heb.BootConfigurations
import com.skb.mytvlibrary.server.service.heb.RespBootSettingInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootUtil @Inject constructor(
    @ApplicationContext private val context: Context, // Application Context 주입
) {
    private val BOOT_CONFIGURATIONS_FILENAME = "boot.conf"
    private val SERVER_LIST_FILENAME = "server-list.conf"

    fun saveBootConfigurations(configs: BootConfigurations?) {
        Timber.d("saveBootConfigurations()")
        saveObjectToFile(configs, BOOT_CONFIGURATIONS_FILENAME)
    }

    fun loadBootConfigurations(): BootConfigurations? {
        Timber.d("loadBootConfigurations()")
        return loadObjectFromFile(BOOT_CONFIGURATIONS_FILENAME) as? BootConfigurations
    }

    fun saveServerList(serverList: List<RespBootSettingInfo.ServerInfo>) {
        Timber.d("saveServerList()")
        saveObjectToFile(serverList, SERVER_LIST_FILENAME)
    }

    fun loadServerList(): List<RespBootSettingInfo.ServerInfo>? {
        Timber.d("loadServerList()")
        return loadObjectFromFile(SERVER_LIST_FILENAME) as? List<RespBootSettingInfo.ServerInfo>
    }

    private fun saveObjectToFile(obj: Any?, filename: String): Boolean {
        Timber.d("saveObjectToFile() - $filename")
        var isOK = false
        var fos: FileOutputStream? = null
        var os: ObjectOutputStream? = null
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE)
            os = ObjectOutputStream(fos)
            if (obj != null) {
                os.writeObject(obj)
            }
            isOK = true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                os?.close()
                fos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return isOK
    }

    private fun loadObjectFromFile(filename: String): Any? {
        Timber.d("loadObjectFromFile() - $filename")
        var result: Any? = null
        var fis: FileInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            fis = context.openFileInput(filename)
            ois = ObjectInputStream(fis)
            result = ois.readObject()
        } catch (fe: FileNotFoundException) {
            fe.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                ois?.close()
                fis?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    companion object {
        private const val TAG = "bootUtil"
    }
}
