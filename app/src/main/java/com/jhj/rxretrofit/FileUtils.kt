package com.jhj.rxretrofit

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileUtils {

    val PROJECT_DIR = "httpcall"

    fun getSDPath(subDir: String?): String? {

        if (Environment.MEDIA_MOUNTED == Environment
                        .getExternalStorageState()) {

            var path = Environment.getExternalStorageDirectory()
                    .absolutePath

            if (!path.endsWith("/"))
                path += "/"

            path += PROJECT_DIR + "/"

            if (subDir != null && subDir.trim { it <= ' ' }.length > 0)
                path += "$subDir/"

            val f = File(path)

            return if (!f.exists()) {
                if (f.mkdirs())
                    path
                else
                    null
            } else {
                if (f.isFile) {
                    if (f.delete()) {
                        if (f.mkdir())
                            path
                        else
                            null
                    } else
                        null
                } else
                    path
            }
        }
        return null
    }

    fun saveFile(inputStream: InputStream, file: File) {
        val os = FileOutputStream(file)
        var bytesRead: Int
        val buffer = ByteArray(1024)
        var process = 0L;

        try {
            do {
                bytesRead = inputStream.read(buffer)
                if (bytesRead == -1) break
                process += bytesRead
                os.write(buffer, 0, bytesRead);
            } while (true)
        } catch (e: Exception) {
            if (file.exists()) {
                file.delete()
            }
            return
        }

    }


    /**
     * @param dir
     * @return
     * 获取下载目录
     */
    fun getSavePath(dir: String): String? {
        val downloadFile = File(Environment.getExternalStorageDirectory(), dir)
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile()
        }
        return downloadFile.absolutePath
    }

    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    public fun getNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

}